package com.nhn.edu.springboot.minidooray.controller;

import com.nhn.edu.springboot.minidooray.dto.ProjectAccountDto;
import com.nhn.edu.springboot.minidooray.dto.ProjectDto;
import com.nhn.edu.springboot.minidooray.properties.ApiProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/project/{projectId}/manage")
public class ProjectManageController {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public ProjectManageController(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @GetMapping
    public ModelAndView getProjectManage(@PathVariable long projectId) {
        /**
         * todo(7)
         *  this page will show delete and modify name button.
         */
        ModelAndView mav = new ModelAndView("projectManage");

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            ResponseEntity<ProjectDto> projectExchange = restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/" + projectId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            mav.addObject("projectName", projectExchange.getBody().getTitle());

        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }

    @PostMapping("/modify")
    public String modifyProjectName(@PathVariable Long projectId,
                                          HttpServletRequest request) {
        /**
         * todo(10)
         *  make function which can modify project name.
         */

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            ProjectDto projectDto = new ProjectDto();
            projectDto.setTitle(request.getParameter("projectName"));

            HttpEntity<ProjectDto> projectDtoEntity = new HttpEntity<>(projectDto, httpHeaders);

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/modify/" + projectId,
                    HttpMethod.PUT,
                    projectDtoEntity,
                    new ParameterizedTypeReference<>() {}
            );

        } catch(Exception e) {
        } finally {
            return "redirect:/project/" + projectId + "/manage";
        }
    }

    @PostMapping("/delete")
    public ModelAndView deleteProject(@PathVariable Long projectId) {
        /**
         * todo(11)
         *  make function which can delete project and other entities associated with Project.
         */

        ModelAndView mav = new ModelAndView("redirect:/project");

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // todo: change api url
            restTemplate.exchange(
                    apiProperties.getTaskEndPoint() + "/project/delete/" + projectId,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // todo: change api url
            ResponseEntity<List<ProjectAccountDto>> projectAccountDtoList = restTemplate.exchange(
                    apiProperties.getAccountEndPoint() + "/project/" + projectId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            for(ProjectAccountDto projectAccountDto : projectAccountDtoList.getBody()) {
                String accountId = projectAccountDto.getAccountId();

                restTemplate.exchange(
                        apiProperties.getAccountEndPoint() + "/project/" + projectId + "/account/" + accountId,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
            }

        } catch(Exception e) {
            mav.setViewName("error");
        }

        return mav;
    }
}
