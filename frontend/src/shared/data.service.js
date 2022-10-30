export const parseList = (response) => {
  console.log("response : {}", response);
  if (response.status !== 200) throw Error(response.message);
  if (!response.data) return [];
  let list = response.data;
  if (typeof list !== "object") {
    list = [];
  }
  return list;
};

export const parseItem = (response, code) => {
  console.log("response : {}", response);
  console.log("code : {}", code);
  if (response.status !== code) throw Error(response.message);
  let item = response.data;
  if (typeof item !== "object") {
    item = undefined;
  }
  return item;
};

export const parseDeleteResponse = (response, code) => {
  console.log("response : {}", response);
  console.log("code : {}", code);
  if (response.status !== code) throw Error(response.message);
  return response.data;
};
