import axios from "axios";

const BASE_URL = "/api/posts";

export const createPost = async (post, token) => {
  const res = await axios.post(BASE_URL, post, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    withCredentials: true,
  });
  return res.data;
};

export const getPosts = async (page = 1, size = 10) => {
  const token = localStorage.getItem("token");
  const res = await axios.get(`/api/posts?page=${page}&size=${size}`, {
    headers: { Authorization: `Bearer ${token}` },
    withCredentials: true,
  });
  return res.data;
};

export const deletePost = async (postId, token) => {
  const res = await fetch(`${BASE_URL}/${postId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    withCredentials: true,
  });

  if (!res.ok) {
    const errorMsg = await res.text();
    throw new Error(errorMsg || "삭제 실패");
  }

  return await res.text();
};

export const updatePost = async (postId, updatedPost, token) => {
  const res = await fetch(`${BASE_URL}/${postId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(updatedPost),
    withCredentials: true,
  });

  if (!res.ok) {
    const errMsg = await res.text();
    throw new Error(errMsg || "수정 실패");
  }

  return await res.text();
};
