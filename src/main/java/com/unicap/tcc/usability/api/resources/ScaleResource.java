package com.unicap.tcc.usability.api.resources;

import com.unicap.tcc.usability.api.models.Scale;
import com.unicap.tcc.usability.api.models.enums.ScalesEnum;
import com.unicap.tcc.usability.api.service.ScaleService;
import com.unicap.tcc.usability.api.utils.UUIDUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/scale")
@Api("Scale API")
@RequiredArgsConstructor
public class ScaleResource {

    private final ScaleService scaleService;

    @GetMapping(path = "/by-uid/{id}")
    public ResponseEntity<Scale> getScaleByUid(@PathVariable @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid id") String id) {
        var scale = scaleService.getScaleByUid(UUID.fromString(id));
        if (ObjectUtils.isEmpty(scale)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .body(scale);
    }

    @GetMapping(path = "/by-acronym/{scaleName}")
    public ResponseEntity<Scale> getScaleByAcronym(@PathVariable("scaleName") @NotNull String scaleName) {
        var optionalEnumScale = ScalesEnum.convert(scaleName);
        if (optionalEnumScale.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        var scale = scaleService.getScaleByScaleEnum(optionalEnumScale.get());
        if (ObjectUtils.isEmpty(scale)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .body(scale);
    }
}
