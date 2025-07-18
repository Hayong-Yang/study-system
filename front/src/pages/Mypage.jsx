import React, { useEffect, useMemo, useState } from "react";
import { getUserInfo, logoutUser } from "../api/member";
import { Link, useNavigate } from "react-router-dom";

export default function Mypage() {
  const [user, setUser] = useState(null);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const token = useMemo(() => localStorage.getItem("token"));

  useEffect(() => {
    getUserInfo(token)
      .then((res) => setUser(res))
      .catch(() => setMessage("사용자 정보를 불러오지 못했습니다."));
  }, [token]);

  if (!user) return <div>로딩 중 ...</div>;

  const handleLogout = async () => {
    await logoutUser(token);
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div>
      <h2>마이페이지</h2>
      <p>아이디: {user.username} </p>
      <p>이름: {user.name}</p>
      <button onClick={handleLogout}>로그아웃</button>
      <hr></hr>
      <h3>메뉴</h3>
      <p>
        <Link to="/editProfile">🐏회원정보 수정🐏</Link>
      </p>
      <p>
        <Link to="/study">💌스터디 게시판💌</Link>
      </p>
      <p>
        <Link to="/study/create">💌스터디 만들기💌</Link>
      </p>
      <button onClick={() => navigate("/my-applications")}>
        신청한 스터디 보기
      </button>
      <p>{message}</p>
    </div>
  );
}
