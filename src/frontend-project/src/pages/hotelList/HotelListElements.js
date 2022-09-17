import styled from "styled-components";

export const ListContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
`;

export const ListWrapper = styled.div`
  width: 100%;
  max-width: 1024px;
  display: flex;
  gap: 20px;
`;

export const ListSearch = styled.div`
  flex: 1;
  background-color: #febb02;
  padding: 10px;
  border-radius: 10px;
  position: sticky;
  top: 10px;
  height: max-content;
`;

export const ListTitle = styled.h1`
  font-size: 20px;
  color: #555;
  margin-bottom: 10px;
`;

export const ListItem = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin-bottom: 10px;
`;

export const ListItemLabel = styled.label`
  font-size: 12px;
`;

export const ListItemInput = styled.input`
  height: 30px;
  border: none;
  padding: 5px;
`;

export const ListOptionSpan = styled.span`
  height: 30px;
  padding: 5px;
  background-color: white;
  display: flex;
  align-items: center;
  cursor: pointer;
`;

export const ListOptions = styled.div`
  padding: 10px;
`;

export const ListOptionItem = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  color: #555;
  font-size: 12px;
`;

export const ListOptionInput = styled.input`
  width: 50px;
`;

export const ListResult = styled.div`
  flex: 3;
`;

export const ListSearchBtn = styled.button`
  padding: 10px;
  background-color: #0071c2;
  color: white;
  border: none;
  width: 100%;
  font-weight: 500;
  cursor: pointer;
`;
