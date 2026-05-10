package com.fleettracking.fleet_tracker.common;

import lombok.Data;

@Data
public class ApiResponse<T> {

	private boolean success;
	private String message;
	private T data;

	public static <T> ApiResponse<T> success(T data) {
		ApiResponse<T> r = new ApiResponse<>();
		r.success = true;
		r.message = "OK";
		r.data = data;
		return r;
	}

	public static <T> ApiResponse<T> error(String message) {
		ApiResponse<T> r = new ApiResponse<>();
		r.success = false;
		r.message = message;
		return r;
	}

}
