import React, { useState } from "react";
import { applyToStudy } from "../api/study";

export default function StudyApplyButton({ studyId }) {
  const [message, setMessage] = useState("");

  const handleApply = async () => {
    try {
      await applyToStudy(studyId);
      setMessage("스터디 신청 완료!");
    } catch (err) {
      if (err.response?.status === 409) {
        setMessage("이미 신청한 스터디입니다.");
      } else if (err.response?.status === 400) {
        setMessage("모집 인원이 초과되었습니다.");
      } else if (err.response?.status === 401) {
        setMessage("로그인이 필요합니다.");
      } else {
        setMessage("신청 실패");
      }
    }
  };

  return (
    <div>
      <button onClick={handleApply}>스터디 신청</button>
      {message && <p style={{ color: "red" }}>{message}</p>}
    </div>
  );
}
