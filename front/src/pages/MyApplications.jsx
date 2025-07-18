import React, { useEffect, useState } from "react";
import axios from "axios";

export default function MyApplications() {
  const [appliedStudies, setAppliedStudies] = useState([]);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await axios.get("/api/study/my-applications", {
          withCredentials: true,
        });
        setAppliedStudies(res.data);
      } catch (err) {
        alert("신청 내역 불러오기 실패");
        console.error(err);
      }
    };

    load();
  }, []);

  return (
    <div>
      <h2>신청한 스터디 목록</h2>
      <ul>
        {appliedStudies.map((study) => (
          <li key={study.id}>
            <strong>{study.title}</strong> (마감일:{" "}
            {new Date(study.maxDate).toLocaleDateString()})
          </li>
        ))}
      </ul>
    </div>
  );
}
