import axios from "axios";

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
        return;
        
    })
    .catch((e)=>{
        console.log(e);
        alert("failed to log in, please check");
        return;
    })
}