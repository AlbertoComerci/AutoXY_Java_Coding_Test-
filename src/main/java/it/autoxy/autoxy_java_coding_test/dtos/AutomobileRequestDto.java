package it.autoxy.autoxy_java_coding_test.dtos;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutomobileRequestDto {
    private int anno;
    private BigDecimal prezzo;
    private int km;
    private Long utenteId;
    private Long marcaId;
    private Long modelloId;
    private Long regioneId;
    private Long statoId;
    private Long alimentazioneId;
}
