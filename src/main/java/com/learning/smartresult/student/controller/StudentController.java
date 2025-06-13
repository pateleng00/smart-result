package com.learning.smartresult.student.controller;

import com.learning.smartresult.student.model.request.AddResult;
import com.learning.smartresult.student.model.request.AddStudent;
import com.learning.smartresult.student.model.request.StudentResultRequest;
import com.learning.smartresult.student.model.response.StudentResultResponse;
import com.learning.smartresult.student.service.IStudentService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
    private final IStudentService studentService;

    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(Exception.class)
        @ResponseBody
        public String handleException(Exception ex) {
            return "Error occurred: " + ex.getMessage();
        }
    }

    @GetMapping("/add-student")
    public String showAddStudentPage(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("newStudent", new AddStudent());
        model.addAttribute("studentsPage", studentService.getAllStudents(pageable));
        return "student-add";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute AddStudent student,
                             RedirectAttributes redirectAttributes) {
        studentService.addStudent(student);
        redirectAttributes.addFlashAttribute("message", "Student added successfully!");
        return "redirect:/student/add-student";
    }

    @GetMapping("/result")
    public String showStudentResultsPage(Model model) {
        model.addAttribute("searchResult", new StudentResultRequest());
        return "student-results"; // Must match template name exactly
    }

    @PostMapping("/get-results")
    public String postStudentResults(@ModelAttribute StudentResultRequest searchResult,
                                     Model model) {
        List<StudentResultResponse> results = studentService.getStudentResults(searchResult);
        model.addAttribute("results", results);
        model.addAttribute("searchResult", searchResult);
        return "student-results"; // Must match template name exactly
    }


    @PostMapping("/add-result")
    public String addStudentResult(@ModelAttribute AddResult addResultRequest,
                                   RedirectAttributes redirectAttributes) {
        studentService.addStudentResult(addResultRequest);
        redirectAttributes.addFlashAttribute("message", "Student result added successfully!");
        return "redirect:/student/add-result";  // <-- fixed!
    }

    @GetMapping("/add-result")
    public String showAddResultPage(Model model) {
        model.addAttribute("addResult", new AddResult());
        return "add-result"; // Must match template name exactly
    }


}