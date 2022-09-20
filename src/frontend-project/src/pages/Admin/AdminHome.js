import React from "react";
import { ViewAllCustomer } from "../../API/AdminApi";
import { AdminNavBar } from "../../components/navbar/NavBar";
import { CContainer, OneCustomer, Text } from "./AdminElemt";

export default function AllCustomers() {
  const { loading, customers, error } = ViewAllCustomer({
    pageNo: 1,
    pageSize: 12,
  });
  const admin = localStorage.getItem("Admin")
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
        <h1>somthing went wrong, please refresh the page</h1>
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
            </OneCustomer>
          );
        })}
      </CContainer>
    </div>
  );
}
