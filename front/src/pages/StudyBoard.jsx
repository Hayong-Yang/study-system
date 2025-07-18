import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getStudies, deleteStudy } from "../api/study";
import "./BoardList.css";
import StudyApplyButton from "../components/StudyApplyButton";
import { getUserInfo } from "../api/member";

export default function StudyBoard() {
  const [studies, setStudies] = useState([]);
  const [totalCount, setTotalCount] = useState(0);
  const [page, setPage] = useState(1);
  const pageSize = 10;
  const totalPages = Math.ceil(totalCount / pageSize);
  const [loading, setLoading] = useState(false);
  const [selectedStudy, setSelectedStudy] = useState(null);
  const [userId, setUserId] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getUserInfo();
        setUserId(userData.id);
      } catch (e) {
        console.error("유저 정보 불러오기 실패", e);
        setUserId(null);
      }
    };

    fetchUser();
  }, []);

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      try {
        const data = await getStudies(page, pageSize);
        setStudies(data.studies);
        setTotalCount(data.totalCount);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [page]);

  const handleDelete = async (studyId) => {
    if (!window.confirm("정말 삭제하시겠습니까?")) return;

    try {
      await deleteStudy(studyId);
      alert("삭제 완료!");
      setSelectedStudy(null);
      setStudies((prev) => prev.filter((s) => s.id !== studyId));
    } catch (err) {
      console.error("삭제 실패:", err);
      alert(err.message || "삭제에 실패했습니다.");
    }
  };

  const handleEdit = (study) => {
    navigate(`/study/edit/${study.id}`, { state: study });
  };

  return (
    <div>
      <h2>스터디 목록</h2>
      <button onClick={() => navigate("/study/create")}>스터디 개설</button>
      <button onClick={() => navigate("/mypage")}>마이페이지</button>
      <button onClick={() => navigate("/my-applications")}>
        신청한 스터디 보기
      </button>

      <ul>
        {studies.map((study) => (
          <li
            key={`study-${study.id}`}
            style={{
              marginBottom: "1em",
              borderBottom: "1px solid #ccc",
              paddingBottom: "0.5em",
              cursor: "pointer",
            }}
            onClick={() => setSelectedStudy(study)}
          >
            <strong>{study.title}</strong> - {study.writerName}
            <br />
            <small>{new Date(study.createdAt).toLocaleString()}</small>
          </li>
        ))}
      </ul>

      {selectedStudy && (
        <div className="modal-overlay" onClick={() => setSelectedStudy(null)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h3>{selectedStudy.title}</h3>
            <p>
              <strong>작성자:</strong> {selectedStudy.writerName}
            </p>
            <p>
              <strong>작성일:</strong>{" "}
              {new Date(selectedStudy.createdAt).toLocaleString()}
            </p>
            <p>
              <strong>모집 인원:</strong> {selectedStudy.maxPeople}명
            </p>
            <p>
              <strong>마감일:</strong>{" "}
              {new Date(selectedStudy.maxDate).toLocaleDateString()}
            </p>
            <hr />
            <p>{selectedStudy.content}</p>

            <StudyApplyButton studyId={selectedStudy.id} />

            {selectedStudy.writerId === userId && (
              <div>
                <button onClick={() => handleEdit(selectedStudy)}>수정</button>
                <button onClick={() => handleDelete(selectedStudy.id)}>
                  삭제
                </button>
              </div>
            )}
            <button onClick={() => setSelectedStudy(null)}>닫기</button>
          </div>
        </div>
      )}

      <div>
        {Array.from({ length: totalPages }, (_, idx) => idx + 1).map((p) => (
          <button
            key={p}
            onClick={() => setPage(p)}
            style={{
              margin: "0 5px",
              fontWeight: page === p ? "bold" : "normal",
            }}
          >
            {p}
          </button>
        ))}
      </div>
    </div>
  );
}
