import axios from "axios";

const BASE_URL = "/api/study";

// 1. 스터디 목록 불러오기 (페이지네이션 포함)
export const getStudies = async (page = 1, size = 10) => {
  const res = await axios.get(`${BASE_URL}?page=${page}&size=${size}`, {
    withCredentials: true,
  });
  return res.data;
};

// 2. 스터디 글 삭제
export const deleteStudy = async (id) => {
  const res = await axios.delete(`${BASE_URL}/delete/${id}`, {
    withCredentials: true,
  });
  return res.data;
};

// 3. 스터디 글 작성
export const createStudy = async (studyData) => {
  const res = await axios.post(`${BASE_URL}/create`, studyData, {
    withCredentials: true,
  });
  return res.data;
};

// 4. 스터디 글 수정
export const updateStudy = async (id, studyData) => {
  const res = await axios.put(`${BASE_URL}/update/${id}`, studyData, {
    withCredentials: true,
  });
  return res.data;
};

// 5. study 신청
export const applyToStudy = async (studyId) => {
  const res = await axios.post(
    `/api/study/apply/${studyId}`,
    {},
    { withCredentials: true }
  );
  return res.data;
};
