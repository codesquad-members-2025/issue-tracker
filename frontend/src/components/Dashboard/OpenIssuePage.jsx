import styles from "./OpenIssuePage.module.css";

function OpenIssuePage() {
  return (
    <div className={styles.openIssuePageContainer}>
      <div className={styles.mainInfo}>
        <button className={styles.checkbox} />
        <div className={styles.issueItem}>
          <div className={styles.issueDetails}>
            <div className={styles.issueIcon}></div>
            <div className={styles.issueTitle}>이슈 제목</div>
            <div className={styles.issueLabel}>Label</div>
          </div>
          <div className={styles.issueMetaInfo}>
            <div>#이슈 번호</div>
            <div>작성자 및 타임스탬프 정보</div>
            <div className={styles.milestone}>
              <div className={styles.milestoneIcon} alt="milestone" />
              <div>마일스톤</div>
            </div>
          </div>
        </div>
      </div>
      <div className={styles.userImage} />
    </div>
  );
}

export default OpenIssuePage;
