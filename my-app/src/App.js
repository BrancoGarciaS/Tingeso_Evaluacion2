//import logo from './logo.svg';
import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import HomeComponent from './components/ModuleHomeComponent';
import AdministrationModule from './components/ModuleAdministration';
import StudentModule from './components/ModuleStudentComponent';

import HeaderComponent from './components/HeaderComponent';

import InformationComponent from './components/InformationComponent';

import ListStudentComponent from './components/StudentListComponent';
import SearchStudentComponent from './components/StudentSearchComponent';
import RegisterStudentComponent from './components/StudentRegisterComponent';
import Acomponent from './components/AComponent';


import InstallmentsList from './components/InstallmentListComponent';
import InstallmentsStudent from './components/InstallmentStudentComponent';
import InstallmentCreate from './components/InstallmentsCreateComponent';

import ExamListComponent from './components/Adm_examsComponent';
import ExamsStudent from './components/Adm_examsStudentComponent';
import AVGcomponent from './components/Adm_examsAVGComponent';
import LoadExcelComponent from './components/Adm_excelComponent';
import ReportComponent from './components/Adm_reportComponent';
import SaveExamsComponent from './components/LoadAVGComponent';


function App() {
  return (
    
    <div class = "fondo">
      
      <HeaderComponent />
      <main className="main-content">
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<HomeComponent />} />
            <Route path="/information" element={<InformationComponent />} />
            <Route path="/adm_module" element={<AdministrationModule />} />
            <Route path="/st_module" element={<StudentModule />} />
            
            <Route path="/installments" element={<InstallmentsList />} />
            <Route path="/installments/student" element={<InstallmentsStudent />} />
            <Route path="/installments/create" element={<InstallmentCreate />} />

            <Route path="/students" element={<ListStudentComponent />} />
            <Route path="/students/register" element={<RegisterStudentComponent />} />
            <Route path="/students/search" element={<SearchStudentComponent />} />
            <Route path="/loadAVG" element={<SaveExamsComponent/>}/>

            <Route path="/exams" element={<ExamListComponent />} />
            <Route path="/exams/student" element={<ExamsStudent />} />
            <Route path="/exams/avg" element={<AVGcomponent />} />
            <Route path="/exams/csv" element={<LoadExcelComponent />} />
            <Route path="/report" element={<ReportComponent />} />

            <Route path="/acomp" element={<Acomponent/>} />


          </Routes>
        </BrowserRouter>
      </main>
    </div>
  );
}

export default App;