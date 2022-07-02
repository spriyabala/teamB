package com.tw.vapsi.biblioteca.controller;
import com.tw.vapsi.biblioteca.controller.helper.ControllerTestHelper;
import com.tw.vapsi.biblioteca.model.CheckedOutBooks;
import com.tw.vapsi.biblioteca.service.ReturnBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReturnBooksController.class)
class ReturnBooksControllerTest extends ControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReturnBookService returnBookService;

    @Test
    @WithMockUser
    void shouldRemoveaCheckedOutBook() throws Exception {
        CheckedOutBooks toBeReturnedchbook1 = new CheckedOutBooks(1,"abc@gmail.com");
        CheckedOutBooks chbook2 = new CheckedOutBooks(2,"def@gmail.com");


        List<CheckedOutBooks> list1 = new ArrayList<CheckedOutBooks>();
        list1.add(toBeReturnedchbook1);
        list1.add(chbook2);
        when(returnBookService.getCheckOutBookList()).thenReturn((list1));

        mockMvc.perform(get("/return/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("returnBookSuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Returned Book successful !!!"));


        verify(returnBookService, times(1)).removeACheckedOutBook(1L);

    }
/*
    @Test
    @WithMockUser
    void shouldDisplayUnsuccessfulReturnOfBookNotCheckedFromLibrary() throws Exception {
        CheckedOutBooks toBeReturnedchbook1 = new CheckedOutBooks(1,"abc@gmail.com");


        List<CheckedOutBooks> list1 = new ArrayList<CheckedOutBooks>();
        list1.add(toBeReturnedchbook1);
        //list1.add(toBeReturnedchbook1);

        when(returnBookService.getCheckOutBookList()).thenReturn((list1));

        mockMvc.perform(get("/return/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("returnBookSuccess"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errormessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errormessage", "Book not Checked Out"));


        verify(returnBookService, times(2)).removeACheckedOutBook(1L);

    }
*/


}