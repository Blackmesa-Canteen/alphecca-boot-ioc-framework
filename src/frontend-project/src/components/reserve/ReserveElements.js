import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export const ReserveContainer = styled.div`
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.418);
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const ReserveWrapper = styled.div`
  background-color: white;
  padding: 20px;
  position: relative;
`;

export const ReserveCloseIcon = styled(FontAwesomeIcon)`
  position: absolute;
  top: 0;
  right: 0;
  cursor: pointer;
`;

export const ReserveBtn = styled.button`
  border: none;
  padding: 10px 20px;
  background-color: #0071c2;
  color: white;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  width: 100%;
  margin-top: 20px;
`;
