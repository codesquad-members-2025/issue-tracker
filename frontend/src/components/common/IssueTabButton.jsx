import styles from "./IssueTabButton.module.css";

function IssueTabButton({ isActive, onClick, iconClassName, issueName }) {
  return (
    <button
      className={`${isActive ? styles.selectedTab : ""} ${styles.tabButton}`}
      onClick={onClick}
    >
      <div className={styles[iconClassName]}></div>
      <span>{issueName}</span>
    </button>
  );
}

export default IssueTabButton;
