import styles from "./IssueList.module.css";
import { useEffect, useState } from "react";
import { API_URL } from "../../constants/link";

const getIssueIconByStatus = (status) => {
  // 이슈 상태에 따라 아이콘을 반환하는 함수
  if (status) {
    return <div className={styles.openIssueIcon}></div>;
  } else {
    return <div className={styles.closedIssueIcon}></div>;
  }
};

function IssueList({ isOpen }) {
  const [issues, setIssues] = useState([]);

  // useEffect를 사용하여 컴포넌트가 마운트될 때 API 호출
  // GET 요청을 통해 이슈 데이터를 가져옴
  // isOpen이 변경될 때마다 API 호출
  useEffect(() => {
    fetch(`${API_URL}/api/issues?is_open=${isOpen}`)
      .then((response) => response.json())
      .then((data) => {
        setIssues(data.issues || []);
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
                {getIssueIconByStatus(isOpen)}
                <div className={styles.issueTitle}>{issue.title}</div>

                {issue.labels &&
                  issue.labels.map((label) => (
                    <div
                      className={styles.issueLabel}
                      key={label.id}
                      style={{
                        backgroundColor: label.color,
                        marginRight: "4px",
                      }}
                    >
                      {label.name}
                    </div>
                  ))}
              </div>
              <div className={styles.issueMetaInfo}>
                <div>#{issue.id}</div>
                <div>
                  {new Date(issue.createdAt).toLocaleString()},{" "}
                  {issue.author.nickname}
                  {"님에 의해 작성되었습니다"}
                </div>
                <div className={styles.milestone}>
                  {issue.milestone !== null && (
                    <>
                      <div className={styles.milestoneIcon} alt="milestone" />
                      <div>{issue.milestone.title}</div>
                    </>
                  )}
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

export default IssueList;
