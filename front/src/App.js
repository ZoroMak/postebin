import React from "react";
import Header from "./components/Header";
import Posts from "./components/filter/Posts";
import AddPost from "./components/add/AddPost";
import FindPost from "./components/findOne/FindPost";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LicenseAgreement from "./components/agreementForm/licenseAgreement/LicenseAgreement";
import LoginForm from "./components/login/LoginForm";
import RegistrationForm from "./components/login/RegistrationForm";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
        showLicenseAgreement: true,
        posts: [],
    };

    this.handleCloseAgreement = this.handleCloseAgreement.bind(this)
  }

  handleCloseAgreement = () => {
      this.setState({ showLicenseAgreement: false });
  };


  render() {
    return (
        <BrowserRouter>
            <Header title="Список постов"/>
            <main>
                <Routes>
                    <Route path="/" element={<AddPost />}/>
                    <Route path="/search" element={<FindPost />}/>
                    <Route path="/filter" element={<Posts />}/>
                    <Route path="/login" element={<LoginForm />}/>
                    <Route path="/regist" element={<RegistrationForm />}/>
                </Routes>
              </main>
            <footer>
                {this.state.showLicenseAgreement && (
                <LicenseAgreement onClose={this.handleCloseAgreement} />
            )}
            </footer>
        </BrowserRouter>
    );
  }
}

export default App;
