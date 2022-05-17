const signup=document.forms.signup;
const checkId=document.getElementById("checkId");
const checkPhone=document.getElementById("checkPhone");
const checkEmail=document.getElementById("checkEmail");

// 아이디 중복 확인
signup.id.addEventListener("blur",(e)=>{
	let v=e.target.value;
	if(v.length>=4){
		fetch("/mem/ajax/findId/"+v)
		.then((resolve,reject)=>{return resolve.json()})
		.then((json)=>{
			if(json){
				checkId.classList.remove("text-primary")
				checkId.classList.add("text-danger");
				checkId.innerText="존재하는 ID";
			}else{
				checkId.classList.remove("text-danger")
				checkId.classList.add("text-primary");
				checkId.innerText="사용 가능";
			};
		});
	}else{
		checkId.classList.remove("text-primary")
		checkId.classList.add("text-danger");
		checkId.innerText="ID의 길이는 4이상입니다.";
	};
});
// 핸드폰 중복 확인
signup.phone.addEventListener("blur",async(e)=>{
	let v=e.target.value;
	let phoneEx=/^01(0|1|6|9)-(\d{3,4})-\d{4}$/;
	if(phoneEx.test(v)){
		let res=await fetch("/mem/ajax/findPhone/"+v);
		let json=await res.json();
		if(json){
			checkPhone.classList.remove("text-primary")
			checkPhone.classList.add("text-danger");
			checkPhone.innerText="존재하는 Phone";
		}else{
			checkPhone.classList.remove("text-danger");
			checkPhone.classList.add("text-primary")
			checkPhone.innerText="사용 가능";
		};
	}else{
		checkPhone.classList.remove("text-primary")
		checkPhone.classList.add("text-danger");
		checkPhone.innerText="핸드폰 형식을 맞추세요!";
	}
});
// 이메일 중복 확인
signup.email.addEventListener("blur",async(e)=>{
	let v=e.target.value;
	let emailEx=/^[0-9a-zA-Z]*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[0-9a-zA-Z]{2,3}$/;
	if(emailEx.test(v)){
		let res=await fetch("/mem/ajax/findEmail/"+v);
		let json=await res.json();
		if(json){
			checkEmail.classList.remove("text-primary")
			checkEmail.classList.add("text-danger");
			checkEmail.innerText="존재하는 Email";
		}else{
			checkEmail.classList.remove("text-danger");
			checkEmail.classList.add("text-primary")
			checkEmail.innerText="사용 가능";
		};	
	}else{
		checkEmail.classList.remove("text-primary")
		checkEmail.classList.add("text-danger");
		checkEmail.innerText="이메일 형식을 맞추세요!";
	}
});









