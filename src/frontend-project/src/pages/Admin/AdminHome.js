import React, {useState}from "react";
import { Button } from "react-bootstrap";
import { ViewAllCustomer } from "../../API/AdminApi";
import { AdminNavBar } from "../../components/navbar/NavBar";
import { CContainer, EditInfoForm, OneCustomer, Text } from "./AdminElemt";




export default function AllCustomers() {
  const { loading, customers, error } = ViewAllCustomer({
    pageNo: 1,
    pageSize: 100,
  });
  const admin = localStorage.getItem("Admin")
  const [editWd, setEditWd] = useState(null);
  const editInfo=(props)=>{
    const info = {
      name: props.userName,
      description:props.description,
      email:props.userId,
      role: "Customer"
    }
    setEditWd(info);
  }
  if(typeof admin ==="undefined" || admin===null){
    window.location="/adminLogin"
  }
  if (loading) {
    return (
      <div>
        <h1>Loading...</h1>
      </div>
    );
  } else if (error!=null) {
    return (
      <div>
        <h1>something went wrong, please refresh the page</h1>
      </div>
    );
  }
  
  return (
    <div>
      <AdminNavBar />
      <h1 style={{marginTop:"2%", marginLeft:"8%"}}>Customers</h1>
      <CContainer>
        {customers.map((c) => {
          return (
            <OneCustomer key={c.userId}>
              <Text>
                <span style={{ fontWeight: "bold" }}>User Name: </span>
                {c.userName}
              </Text>
              <Text>
                <span style={{ fontWeight: "bold" }}>User ID: </span> {c.userId}
              </Text>
              <Text>
                <span style={{ fontWeight: "bold" }}>Description: </span>{" "}
                {c.description}
              </Text>
              <Text>
                <span style={{ fontWeight: "bold" }}>Create Time: </span>{" "}
                {new Date(c.createTime).toUTCString().slice(0, 17)}
              </Text>
              <div style={{marginLeft:"5%"}}><Button onClick={()=>{editInfo(c)}}>Edit</Button></div>
            </OneCustomer>
          );
        })}
        {editWd!==null&&<EditInfoForm value={editWd} onCancel={()=>{setEditWd(null)}}/>}
      </CContainer>
    </div>
  );
}
