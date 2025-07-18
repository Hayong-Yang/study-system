import React, { useCallback, useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getPosts, deletePost } from "../api/board";
import { parseJwt } from "../util/jwtUtil";
import "./BoardList.css";
export default function BoardList() {
  const [posts, setPosts] = useState([]);
  const [totalCount, setTotalCount] = useState(0);
  const pageSize = 10;
  const totalPages = Math.ceil(totalCount / pageSize);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [selectedPost, setSelectedPost] = useState(null);
  const token = localStorage.getItem("token");
  const user = parseJwt(token);
  const userId = user?.id;
  useEffect(() => {
    const load = async () => {
      setLoading(true);
      try {
        const data = await getPosts(page, pageSize);
        setPosts(data.posts);
        setTotalCount(data.totalCount);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [page]);

  const navigate = useNavigate();

  // 글 삭제
  const handleDelete = async (postId) => {
    if (!window.confirm("정말 삭제하시겠습니까?")) return;

    try {
      await deletePost(postId, token);
      alert("삭제 완료!");
      setSelectedPost(null);
      setPosts((prev) => prev.filter((p) => p.id !== postId));
    } catch (err) {
      console.error("삭제 실패:", err);
      alert(err.message || "삭제에 실패했습니다.");
    }
  };

  // 글 수정
  const handleEdit = (post) => {
    navigate(`/board/edit/${post.id}`, { state: post });
  };

  // 화면
  return (
    <div>
      <h2>게시판</h2>
      <button onClick={() => navigate("/board/write")}>글쓰기</button>
      <ul>
        {posts.map((post, idx) => (
          <li
            key={`post-${post.id}`}
            style={{
              marginBottom: "1em",
              borderBottom: "1px solid #ccc",
              paddingBottom: "0.5em",
              cursor: "pointer",
            }}
            onClick={() => setSelectedPost(post)}
          >
            <strong>{post.title}</strong> - {post.writerName}
            <br />
            <small>{new Date(post.createdAt).toLocaleString()}</small>
          </li>
        ))}
      </ul>
      {selectedPost && (
        <div className="modal-overlay" onClick={() => setSelectedPost(null)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h3>{selectedPost.title}</h3>
            <p>
              <strong>작성자:</strong> {selectedPost.writerName}
            </p>
            <p>
              <strong>작성일:</strong>{" "}
              {new Date(selectedPost.createdAt).toLocaleString()}
            </p>

            <hr />
            <p>{selectedPost.content}</p>

            {/* 로그인한 유저와 글쓴이가 일치할 경우에만 보임 */}
            {selectedPost.writerId === userId && (
              <div>
                <button onClick={() => handleEdit(selectedPost)}>수정</button>
                <button onClick={() => handleDelete(selectedPost.id)}>
                  삭제
                </button>
              </div>
            )}

            <button onClick={() => setSelectedPost(null)}>닫기</button>
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
