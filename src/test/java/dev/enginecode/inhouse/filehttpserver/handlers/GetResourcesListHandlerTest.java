package dev.enginecode.inhouse.filehttpserver.handlers;

import dev.enginecode.inhouse.filehttpserver.service.GetResourcesListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetResourcesListHandlerTest {
    @Mock
    private GetResourcesListService filesListService;
    @Mock
    private Model model;

    private GetResourcesListHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetResourcesListHandler(filesListService);
    }

    @Test
    void shouldReturnCorrectResourcesListForDirectory() {
        // given
        String requestURI = "/test_directory";
        String pathFromRoot = "/test_directory";
        String rootDirectory = "/root";
        when(filesListService.getAll(rootDirectory + pathFromRoot))
                .thenReturn(List.of("file1", "file2"));
        when(model.addAttribute(eq("currentPath"), eq(pathFromRoot))).thenReturn(model);
        when(model.addAttribute(eq("folders"), anyList())).thenReturn(model);
        when(model.addAttribute(eq("files"), anyList())).thenReturn(model);

        // when
        String result = handler.handle(requestURI, model);

        // then
        assertEquals("files-list", result);
        verify(model).addAttribute("currentPath", pathFromRoot);
        verify(model).addAttribute("folders", new ArrayList<>());
        verify(model).addAttribute("files", List.of("file1", "file2"));
    }
}