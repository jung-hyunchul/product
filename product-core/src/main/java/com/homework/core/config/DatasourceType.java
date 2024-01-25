package com.homework.core.config;

/** Datasource의 타입으로, 기본적으로 Master, RR 두 개를 지원합니다. */
public enum DatasourceType {
  /** PRIMARY(Master) Datasource */
  WRITER,

  /** Read-only(RR) Datasource */
  READER
}
