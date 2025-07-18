import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createPost } from "../api/board";
export default function BoardWrite() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const handleSubmit = async () => {
    if (!title || !content) {
      setMessage(":x: 제목과 내용을 모두 입력하세요.");
      return;
    }
    const post = { title, content };
    try {
      await createPost(post, token);
      navigate("/board");
    } catch (e) {
      setMessage("글쓰기 실패!🤢");
    }
  };
  return (
    <div>
      <h2>글쓰기</h2>
      <p>
        제목: <input value={title} onChange={(e) => setTitle(e.target.value)} />
      </p>
      <p>
        내용:
        <br />
        <textarea
          rows="8"
          cols="50"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
      </p>
      <button onClick={handleSubmit}>등록</button>
      <p>{message}</p>
    </div>
  );
}
