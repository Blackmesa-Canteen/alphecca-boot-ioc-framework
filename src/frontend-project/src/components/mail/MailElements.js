import styled from "styled-components";

export const MailContainer = styled.div`
  width: 100%;
  margin-top: 50px;
  background-color: black;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  padding: 50px;
`;

export const MailInput = styled.input`
  width: 300px;
  height: 30px;
  padding: 10px;
  border: none;
  margin-right: 10px;
  border-radius: 5px;
`;

export const MailButton = styled.button`
  height: 50px;
  background-color: #008000;
  border-radius: 15px;
  color: white;
  font-weight: 500;
  border: none;
  cursor: pointer;
`;
