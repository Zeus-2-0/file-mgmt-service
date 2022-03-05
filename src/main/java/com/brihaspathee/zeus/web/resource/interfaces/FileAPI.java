package com.brihaspathee.zeus.web.resource.interfaces;

import com.brihaspathee.zeus.web.model.FileDetailDto;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, March 2022
 * Time: 9:33 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.resource.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@RequestMapping("/api/v1/file-mgmt")
@Validated
public interface FileAPI {
    /**
     *
     * @param fileId
     * @return
     */
    @Operation(
            method = "GET",
            description = "Get the details of the file by its id",
            tags = {"file-mgmt"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the details of the file",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ZeusApiResponse.class))
                            }
                    )
            }
    )
    @GetMapping(path="/{fileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ZeusApiResponse<FileDetailDto>> getFileDetail(@PathVariable("fileId") String fileId);
}
