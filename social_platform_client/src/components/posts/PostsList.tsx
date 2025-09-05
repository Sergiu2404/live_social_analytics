import { FaUserCircle } from "react-icons/fa";
import "./PostsList.css";

const posts = [
  { id: 1, username: "alice", img: "https://placebear.com/600/600", caption: "d1" },
  { id: 2, username: "bob", img: "https://placebear.com/600/600", caption: "d2" },
  { id: 3, username: "charlie", img: "https://placebear.com/600/600", caption: "d3" },
];

export default function PostsList() {
  return (
    <div className="posts-list">
      {posts.map((post) => (
        <div className="post" key={post.id}>
          <div className="post-header">
            <FaUserCircle className="avatar" />
            <span className="username">{post.username}</span>
          </div>
          <img src={post.img} alt={post.caption} className="post-image" />
          <div className="post-footer">
            <p>
              <strong>{post.username}</strong> {post.caption}
            </p>
          </div>
        </div>
      ))}
    </div>
  );
}
