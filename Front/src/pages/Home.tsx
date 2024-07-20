import { Link } from "react-router-dom"


const Home = () => {

   return(
      <div>
         <Link to="/signup">회원가입</Link> <br />
         <Link to="/signin">로그인</Link> <br />
         <button>로그아웃</button>
      </div>
   )
}

export default Home