import { FaUserCircle, FaSearch } from "react-icons/fa";
import "./ChatsList.css";

const chats = [
  { id: 1, user: "alice", lastMsg: "See you soon!" },
  { id: 2, user: "bob", lastMsg: "Let's meet tomorrow." },
  { id: 3, user: "charlie", lastMsg: "Coffee? ☕" },
];

export default function ChatsList() {
  return (
    <aside className="chats-list">
      <h3>Chats</h3>
      <div className="chats-search">
        <FaSearch className="icon" />
        <input type="text" placeholder="Search chats..." />
      </div>
      <ul>
        {chats.map((chat) => (
          <li key={chat.id}>
            <FaUserCircle className="avatar" />
            <div>
              <strong>{chat.user}</strong>
              <p>{chat.lastMsg}</p>
            </div>
          </li>
        ))}
      </ul>
    </aside>
  );
}
