import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { updatePost } from "../api/board";

export default function BoardEdit() {
  const location = useLocation();
  const navigate = useNavigate();
  const post = location.state;

  const [title, setTitle] = useState(post.title);
  const [content, setContent] = useState(post.content);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    try {
      await updatePost(post.id, { title, content }, token);
      alert("수정 완료!");
      navigate("/board");
    } catch (err) {
      console.error("수정 실패:", err);
      alert("수정 중 오류 발생");
    }
  };

  return (
    <div>
      <h2>게시글 수정</h2>
      <form onSubmit={handleSubmit}>
        <input
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="제목"
        />
        <br />
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="내용"
        />
        <br />
        <button type="submit">저장</button>
        <button onClick={() => navigate("/board")}>취소</button>
      </form>
    </div>
  );
}
