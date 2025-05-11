import styles from "./OpenIssuePage.module.css";
import { useEffect, useState } from "react";

function OpenIssuePage(isOpen) {
  const [issues, setIssues] = useState([]);

  // useEffect를 사용하여 컴포넌트가 마운트될 때 API 호출
  // GET 요청을 통해 이슈 데이터를 가져옴
  // isOpen이 변경될 때마다 API 호출
  useEffect(() => {
    fetch("http://localhost:3000/api/issues")
      .then((response) => response.json())
      .then((data) => {
        setIssues(data.issues);
      })
      .catch((error) => {
        console.error("Error fetching issue data:", error);
      });
  }, [isOpen]);

  return (
    <div className={styles.issueListContainer}>
      {issues.map((issue) => (
        <div className={styles.IssueContainer} key={issue.id}>
          <div className={styles.mainInfo} key={issue.id}>
            <button className={styles.checkbox} />
            <div className={styles.issueItem}>
              <div className={styles.issueDetails}>
                <div className={styles.issueIcon}></div>
                <div className={styles.issueTitle}>{issue.title}</div>
                <div className={styles.issueLabel}>{issue.labels[0].name}</div>
              </div>
              <div className={styles.issueMetaInfo}>
                <div>#{issue.id}</div>
                <div>
                  {new Date(issue.createdAt).toLocaleString()},{" "}
                  {issue.author.nickname}
                  {"님에 의해 작성되었습니다"}
                </div>
                <div className={styles.milestone}>
                  <div className={styles.milestoneIcon} alt="milestone" />
                  <div>{issue.milestone.title}</div>
                </div>
              </div>
            </div>
          </div>

          <div className={styles.userImage} />
        </div>
      ))}
    </div>
  );
}

export default OpenIssuePage;
