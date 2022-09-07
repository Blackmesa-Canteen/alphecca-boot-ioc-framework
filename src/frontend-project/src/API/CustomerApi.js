import axios from "axios";

const BASE_URL = "http://localhost:8088/";

//customer signup
export async function signup(user){
    const endpoint = BASE_URL+`api/customer/`
    const {userId, userName, password1, password2} = user;
    if(!userId){
        alert("Please provide your email address");
        return
    }
    if(!password1){
        alert("Please enter password");
        return
    }
    if(!userName){
        alert("Please enter your name");
        return;
    }
    if(password1!==password2){
        alert("Passwords do not match, please check");
        return;
    }

    axios({
        url:endpoint,
        headers:{
            "Content-Type": "application/json",
        },
        method: "POST",
        data:{
            userId: userId,
            userName:userName,
            password:password1,
        },
    })
    .then(()=>{
        console.log("successfully create a account");
        
    })
    .catch((e)=>{
        console.log(e);
        alert("An error occured")
    })
}

export async function customerLogin(user){
    const endpoint = BASE_URL+`api/customer/login/`
    const {userId, password} = user;
    
    axios({
        url:endpoint,
        method:"POST",
        headers:{
            "Content-Type":"application/json",
        },
        data:JSON.stringify(
            {
                userId: userId,
                password:password
        },
        {withCrednetials:true}
        ),
    })
    .then((res)=>{
        localStorage.setItem("token", res.data.data.token);
        alert("logged in");
        window.location="/"
        return;
        
    })
    .catch((e)=>{
        console.log(e);
        alert("failed to log in, please check");
        return;
    })
}
export async function customerLogout(){
    const endpoint = BASE_URL+`api/customer/logout`
    axios({
        url:endpoint,
        method:"GET",
        headers:{
            "Authorization":localStorage.getItem("token"),
        },
    })
    .then((res)=>{
        console.log(res);
        localStorage.clear();
        alert("Successfully logged out")
    })
    .catch((e)=>{
        console.log(e);
    })
}