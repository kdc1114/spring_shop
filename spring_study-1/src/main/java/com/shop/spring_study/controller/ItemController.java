package com.shop.spring_study.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shop.spring_study.repository.ItemRepository;
import com.shop.spring_study.vo.ItemVo;

@Controller
@RequestMapping("/item")
public class ItemController {
	// 실제로는 이미지서버주소를 따로 만들기때문에 서버주소이미지 적으면된다.
	@Value("${spring.servlet.multipart.location}")
	private String savePath;
	
	@Autowired
	private ItemRepository ir;
	
	@GetMapping("/list.do")
	public String list(Model m) {
		m.addAttribute("itemList", ir.findAllByOrderByPostTimeDesc());
		return "/item/list";
	}
	@GetMapping("/queryList.do")
	public String queryList(Model model) {
		Iterable<Object[]> Entitys_list=ir.findAllWithCategoryWithMember();
		model.addAttribute("itemList",Entitys_list);
		/*for(Object[] entitys : Entitys_list) {
			Object item=entitys[0];
			Object member=entitys[1];
			Object category=entitys[2];
			System.out.println(item);
			System.out.println(member);
			System.out.println(category);
		}*/
		return "/item/queryList";
	}
	@GetMapping("/entityGraphList.do")
	public String entityGraphList(Model model) {
		model.addAttribute("itemList",ir.findAllByOrderByPostTime());
//		System.out.println(ir.findAllByOrderByPostTime());
		return "/item/entityGraphList";
	}
	@GetMapping("/list/{page}")
	public String pageableList(@PathVariable int page,
							   @RequestParam(defaultValue = "postTime") String sort,
							   @RequestParam(defaultValue = "desc") String desc,
							   Model model) {
		int size=5;
		Pageable pageable=null;
		if(desc.equals("desc")) {
			// 1번 페이지로 알고있지만 0번부터 시작해서 page에 -1
			// MYSQL limit(start,size)
			pageable=PageRequest.of(page-1, size, Sort.by(sort).descending());
		}else if(desc.equals("asc")) {
			pageable=PageRequest.of(page-1, size, Sort.by(sort).ascending());
		}
		/* 모든 JPA는 함수에 아무런 제약없이 Pageable을 매개변수로 정의할 수 있고
		   Iterable을 부모로 하고 totalPage nextPage total과 같이 page 에 필요한
		   정보를 수집하는 필드를 갖는 Page를 Return 받을 수 있다. */
		Page<ItemVo> itemList=ir.findAll(pageable);
		
		System.out.println("화면에 출력할 size : "+itemList.getSize());
		System.out.println("전체페이지 수 : "+itemList.getTotalPages());
		System.out.println("현재페이지의 다음이 있는지? : "+itemList.hasNext());
		System.out.println("현재페이지의 이전이 있는지? : "+itemList.hasPrevious());
		System.out.println("total row : "+itemList.getTotalElements());
		System.out.println("현재페이지 : "+(itemList.getNumber()+1));
		
		model.addAttribute("itemList", itemList);
		return "/item/pageableList";
	}
	@GetMapping("/seller/insert.do")
	public String insertForm() {
		return "/item/insert";
	}
	@PostMapping("/seller/insert.do")
	public String insert(ItemVo item,MultipartFile mainImgFile,MultipartFile detailImgFile,HttpSession session) {
		boolean insert=true;
		if(!mainImgFile.isEmpty()) {
			String mainImgExt=mainImgFile.getContentType().split("/")[1]; // "image/jpeg"
			String mainImgType=mainImgFile.getContentType().split("/")[0]; // "image/jpeg"
			String mainFileName="item_main_"+System.currentTimeMillis()+"_"+(int)(Math.random()*10000);
			Path mainImgFilePath=Paths.get(savePath+"/"+mainFileName+"."+mainImgExt);
			if(mainImgType.equals("image")) {
				try {
					//임시 버퍼로 생성된 파일을 실제 파일로 옮겨서 저장하는 함수
					mainImgFile.transferTo(mainImgFilePath);
					// 저장된 원본파일 불러오기
					File mainFile=new File(savePath+"/"+mainFileName+"."+mainImgExt);
					BufferedImage mainImg=ImageIO.read(mainFile);
					BufferedImage mainThumbImg=new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
					mainThumbImg.getGraphics().drawImage(mainImg, 0, 0, 100, 100, null);
					ImageIO.write(mainThumbImg, mainImgExt, new File(savePath+"/thumb/"+mainFileName+"."+mainImgExt));
					item.setMainImg(mainFileName+"."+mainImgExt);
					ir.save(item);
				} catch (IllegalStateException | IOException e) {
					insert=false;
					e.printStackTrace();
				}
			}
		}else {
			insert=false;
		}
		if(insert) {
			session.setAttribute("msg", "아이템 저장 성공");
			return "redirect:/item/list/1";
		}else {
			session.setAttribute("msg", "아이템 저장 실패");
			return "redirect:/item/seller/insert.do";	
		}
	}
}






