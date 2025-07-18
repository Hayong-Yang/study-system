import React, { useEffect, useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Mypage from "./pages/Mypage";
import EditProfile from "./pages/EditProfile";
import BoardWrite from "./pages/boardWrite";
import Board from "./pages/boardList";
import BoardEdit from "./pages/boardEdit";
import CreateStudy from "./pages/CreateStudy";
import StudyBoard from "./pages/StudyBoard";
import MyApplications from "./pages/MyApplications";

import { getUserInfo } from "./api/member";
import PrivateRoute from "./components/PrivateRoute";

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(null);

  useEffect(() => {
    const checkLogin = async () => {
      try {
        await getUserInfo();
        setIsLoggedIn(true);
      } catch (e) {
        setIsLoggedIn(false);
      }
    };
    checkLogin();
  }, []);

  if (isLoggedIn === null) return <div>로그인 상태 확인 중...</div>;

  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/mypage"
        element={
          <PrivateRoute isLoggedIn={isLoggedIn}>
            <Mypage />
          </PrivateRoute>
        }
      />
      <Route
        path="/editProfile"
        element={
          <PrivateRoute isLoggedIn={isLoggedIn}>
            <EditProfile />
          </PrivateRoute>
        }
      />
      <Route
        path="/study/create"
        element={
          <PrivateRoute isLoggedIn={isLoggedIn}>
            <CreateStudy />
          </PrivateRoute>
        }
      />
      <Route
        path="/study"
        element={
          <PrivateRoute isLoggedIn={isLoggedIn}>
            <StudyBoard />
          </PrivateRoute>
        }
      />
      <Route
        path="/my-applications"
        element={
          <PrivateRoute isLoggedIn={isLoggedIn}>
            <MyApplications />
          </PrivateRoute>
        }
      />

      <Route
        path="/board"
        element={
          <PrivateRoute isLoggedIn={isLoggedIn}>
            <Board />
          </PrivateRoute>
        }
      />
      <Route
        path="/board/write"
        element={
          <PrivateRoute isLoggedIn={isLoggedIn}>
            <BoardWrite />
          </PrivateRoute>
        }
      />
      <Route
        path="/board/edit/:id"
        element={
          <PrivateRoute isLoggedIn={isLoggedIn}>
            <BoardEdit />
          </PrivateRoute>
        }
      />
    </Routes>
  );
}
