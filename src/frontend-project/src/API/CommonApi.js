import axios from "axios";

export const BASE_URL = "https://swen90007-alphecca-backend-app.herokuapp.com/api/";

//customer signup
export async function signup(user) {
  const { role, userId, userName, password1, password2 } = user;
  var endpoint;
  role === "Customer"
    ? (endpoint = BASE_URL + `customer/`)
    : (endpoint = BASE_URL + `hotelier/`);
  console.log(endpoint);
  console.log(user);
  if (!userId) {
    alert("Please provide your email address");
    return;
  }
  if (!password1) {
    alert("Please enter password");
    return;
  }
  if (!userName) {
    alert("Please enter your name");
    return;
  }
  if (password1 !== password2) {
    alert("Passwords do not match, please check");
    return;
  }

  axios({
    url: endpoint,
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    data: {
      userId: userId,
      userName: userName,
      password: password1,
    },
  })
    .then(() => {
      alert("successfully create a account");
      window.location = "/";
    })
    .catch((e) => {
      console.log(e);
      alert("An error occured");
    });
}

export async function Login(user) {
  var endpoint;
  const { role, userId, password } = user;
  if (role === "Customer") {
    endpoint = BASE_URL + `customer/login`;
  } else if (role === "Hotelier") {
    endpoint = BASE_URL + `hotelier/login`;
  } else {
    endpoint = BASE_URL + `admin/login`;
  }

  axios({
    url: endpoint,
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: JSON.stringify(
      {
        userId: userId,
        password: password,
      },
      { withCrednetials: true }
    ),
  })
    .then((res) => {
      localStorage.setItem(role, res.data.data.token);
      alert("successfully logged in");
      window.location = "/" + role.toLowerCase();
      return;
    })
    .catch((e) => {
      console.log(e);
      alert("failed to log in, please check");
      return;
    });
}

export async function Logout(role) {
  var endpoint;
  if (role === "Customer") {
    endpoint = BASE_URL + `customer/logout`;
  } else if (role === "Hotelier") {
    endpoint = BASE_URL + `hotelier/logout`;
  } else {
    endpoint = BASE_URL + `admin/logout`;
  }
  axios({
    url: endpoint,
    method: "GET",
    headers: {
      Authorization: localStorage.getItem(role),
    },
  })
    .then((res) => {
      console.log(res);
      localStorage.removeItem(role);
      alert("Successfully logged out");
      window.location = "/";
    })
    .catch((e) => {
      console.log(e);
    });
}
