import { FaUserCircle } from "react-icons/fa";
import "./Posts.css";

const posts = [
  {
    id: 1,
    username: "User Name",
    img: "https://placekitten.com/500/600",
    caption: "Mocked description 🐱",
  },
  {
    id: 2,
    username: "janedoe",
    img: "https://placebear.com/500/600",
    caption: "Hiking adventures 🏔️",
  },
  {
    id: 3,
    username: "alex",
    img: "https://picsum.photos/500/600",
    caption: "Coffee time ☕",
  },
];

export default function Posts() {
  return (
    <div className="posts-container">
      {posts.map((post) => (
        <div className="post" key={post.id}>
          <div className="post-header">
            <FaUserCircle className="avatar" />
            <span className="username">{post.username}</span>
          </div>
          <img src={post.img} alt="post" className="post-image" />
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
