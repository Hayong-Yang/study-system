import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createStudy } from "../api/study";

export default function CreateStudy() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [maxPeople, setMaxPeople] = useState(1);
  const [maxDate, setMaxDate] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await createStudy({ title, content, maxPeople, maxDate });
      navigate("/study");
    } catch (err) {
      setMessage("스터디 개설 실패");
    }
  };

  return (
    <div>
      <h2>스터디 개설</h2>
      <form onSubmit={handleSubmit}>
        <p>
          제목:{" "}
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </p>
        <p>
          설명:{" "}
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </p>
        <p>
          모집 인원:{" "}
          <input
            type="number"
            min="1"
            value={maxPeople}
            onChange={(e) => setMaxPeople(e.target.value)}
            required
          />
        </p>
        <p>
          모집 마감일:{" "}
          <input
            type="datetime-local"
            value={maxDate}
            onChange={(e) => setMaxDate(e.target.value)}
            required
          />
        </p>
        <button type="submit">개설하기</button>
      </form>
      <p>{message}</p>
    </div>
  );
}
