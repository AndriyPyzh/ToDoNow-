package andriypyzh.servlets.actions.edit;

import andriypyzh.entity.Task;
import andriypyzh.services.TaskService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/EditTask")
public class EditTaskServlet extends HttpServlet {
    Logger logger = Logger.getLogger(EditTaskServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskService taskService = new TaskService();

        HttpSession session = request.getSession(false);
        String section = (String) session.getAttribute("section");
        int taskId=0;
        try {
            taskId = (int) session.getAttribute("edittask");
        } catch (NumberFormatException e){

        }

        String taskName = (String) request.getParameter("Name");
        int priority = Integer.parseInt(request.getParameter("Priority"));
        java.sql.Date deadline = java.sql.Date.valueOf(request.getParameter("Deadline"));
        String description = request.getParameter("Description");

        Task task = taskService.getByID(taskId);

        task.setName(taskName);
        task.setPriority(priority);
        task.setExpirationDate(deadline);
        task.setDescription(description);

        taskService.updateTask(task);

        if (section.startsWith("Tasks of ")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/home");
            requestDispatcher.forward(request, response);
        } else {
            logger.info("/Projects?project=" + section);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Projects?project=" + section);
            requestDispatcher.forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String section = (String) session.getAttribute("section");

        int taskId = Integer.parseInt(request.getParameter("task"));

        TaskService taskService = new TaskService();
        Task task = taskService.getByID(taskId);

        session.setAttribute("edittask", taskId);

        String name = task.getName();
        int priority = task.getPriority();
        String deadline = task.getExpirationDate().toString();
        String description = task.getDescription();


        request.setAttribute("name", name);
        request.setAttribute("priority", priority);
        request.setAttribute("deadline", deadline);
        request.setAttribute("description", description);


        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/forms/edittask.jsp");
        requestDispatcher.forward(request, response);
    }

}