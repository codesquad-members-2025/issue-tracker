import styles from "./Header.module.css";
import logo from "../../assets/images/logoMedium.svg";
import userProfile from "../../assets/images/userImage.png";
// userProfile 이미지를 불러오는 코드는 추후 변경되어야할 부분

function Header() {
  return (
    <div className={styles.headerWrapper}>
      <img src={logo} alt="logo" className={styles.logo} />
      <img
        src={userProfile}
        alt="userProfileImage"
        className={styles.userProfile}
      />
    </div>
  );
}

export default Header;
