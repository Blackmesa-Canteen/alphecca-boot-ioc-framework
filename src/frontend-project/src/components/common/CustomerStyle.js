/**
 * style of common items
 * includes button, login/signup form, Alphecca logo
 */
import styled, { createGlobalStyle } from "styled-components";
import Logo from "../../Picture/Icon.png";
export const GlobalStyle = createGlobalStyle`
body {
  background: rgba(200,200,200,0.25);
  
};

`;

export const FormContainer = styled.div`
  box-shadow: 0 3px 3px rgba(0, 0, 0, 0.1);
  border-radius: 6px;
  background-color: rgba(0, 0, 0, 0.03);
  backdrop-filter: blur(50px);
  width: 350px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem 2rem;
  margin-top: 3%;
`;
export const RoleCardContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;
export const RoleCard = styled(FormContainer)`
  width: 300px;
  height: 300px;
  margin-right: 1%;
  &:hover {
    -webkit-transform: scale(1.03);
    transform: scale(1.03);
    -webkit-transition: 0.3s ease-in-out;
    transition: 0.3s ease-in-out;
  }
`;

export const Input = styled.input`
  width: 90%;
  height: 42px;
  border-radius: 5px;
  outline: none;
  border: 1px solid transparent;
  padding: 0 10px;
  border-bottom: 2px solid rgba(200, 200, 200, 1);
  margin-top: 9px;
  &::placeholder {
    color: rgba(200, 200, 200, 1);
  }
  &:not(:last-of-type) {
    border-bottom: 3px solid rgba(200, 200, 200, 0, 4);
  }
  &:focus {
    outline: none;
    border-bottom: 3px solid rgb(69, 146, 88);
  }
`;
export const CheckboxContainer = styled.div`
  width: 100%;
  margin-top: 10px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;
export const MutedLink = styled.a`
  font-size: 11px;
  color: rgba(151, 188, 222, 1);
  font-weight: 500;
  text-decoration: none;
`;
export const SubmitButton = styled.button`
  width: 50%;
  height: 50px;
  padding: 10px 4%;
  font-size: 15px;
  font-weight: 600;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: all, 0.2s ease-in-out;
  background: rgb(69, 146, 88);
  margin-top: 10px;
  margin-bottom: 20px;
  &:hover {
    filter: brightness(1.1);
  }
`;
export const IconContainer = styled.footer`
  margin-top: 3%;
`;

const LogoContainer = styled.div`
  width: 150px;
  display: flex;
  flex-direction: row;
  font-size: 20px;
  font-weight: 600;
  color: white;
  align-items: center;
  margin-left: 10%;
  margin-bottom: 5px;
`;
export function AlpheccaLogo() {
  return (
    <LogoContainer>
      <img src={Logo} alt="logo" height="40" width="40" />
      <div>Alphecca</div>
    </LogoContainer>
  );
}
