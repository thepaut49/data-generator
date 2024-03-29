export const removeAccentToLowerCase = (text) => {
  return text
    .toLowerCase()
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "");
};

export const removeAccent = (text) => {
  return text.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
};

export const searchLike = (stringToSearch, text) => {
  const parsed = text
    .toLowerCase()
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "");
  return (
    parsed.includes(stringToSearch.toLowerCase()) ||
    text.toLowerCase().includes(stringToSearch.toLowerCase())
  );
};

export const isStringNullOrUndefinedOrEmpty = (text) => {
  if (text === null || text.trim() === "") {
    return true;
  } else {
    return false;
  }
};

export const isStringNotEmpty = (text) => {
  if (text === null || text.trim() === "") {
    return false;
  } else {
    return true;
  }
};
