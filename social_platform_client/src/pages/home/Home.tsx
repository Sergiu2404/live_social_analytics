import React from "react";
import "./Home.css";
import { FaUserCircle, FaCog, FaSearch, FaFacebookMessenger } from "react-icons/fa";
import PostsList from "../../components/posts/PostsList";
import ChatsList from "../../components/chats/ChatsList";

export default function Home() {
  return (
    <div className="home-container">
      {/* navbar */}
      <header className="navbar">
        <div className="logo">App Logo</div>
        <div className="search-bar">
          <FaSearch className="icon" />
          <input type="text" placeholder="Search users..." />
        </div>
        <div className="nav-icons">
          <FaFacebookMessenger className="icon" title="Chats" />
          <FaUserCircle className="icon" title="Profile" />
          <FaCog className="icon" title="Settings" />
        </div>
      </header>

      <main className="content">
        {/* centered feed */}
        <section className="feed">
          <PostsList />
        </section>

        {/* chats sidebar */}
        <ChatsList />
      </main>
    </div>
  );
}
