import axios from "axios";

const BASE_URL = "/api/member";

export const loginUser = async (username, password) => {
  const params = new URLSearchParams();
  params.append("username", username);
  params.append("password", password);

  try {
    const res = await axios.post("/login", params, {
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      withCredentials: true,
    });

    return true;
  } catch (err) {
    return false;
  }
};

export const registerUser = async (user) => {
  const res = await axios.post(`${BASE_URL}/register`, user, {
    withCredentials: true,
  });
  return res.data;
};

export const getUserInfo = async () => {
  const res = await axios.get(`${BASE_URL}/info`, {
    withCredentials: true,
  });
  return res.data;
};

export const logoutUser = async () => {
  const res = await axios.post("/logout", null, {
    withCredentials: true,
  });
  return res.data;
};

export const updateUser = async (user, token) => {
  const res = await axios.put(`${BASE_URL}/update`, user, {
    headers: { Authorization: `Bearer ${token} ` },
    withCredentials: true,
  });
  return res.data;
};
