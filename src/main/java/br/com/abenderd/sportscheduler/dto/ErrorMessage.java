package br.com.abenderd.sportscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {
  private String message;
}
