/*
 * pragmatickm-task-servlet - Tasks nested within SemanticCMS pages and elements in a Servlet environment.
 * Copyright (C) 2013, 2014, 2015, 2016, 2021, 2022, 2025, 2026  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of pragmatickm-task-servlet.
 *
 * pragmatickm-task-servlet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pragmatickm-task-servlet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with pragmatickm-task-servlet.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.pragmatickm.task.servlet;

import static com.aoapps.servlet.http.Cookies.addCookie;
import static com.aoapps.servlet.http.Cookies.getCookie;
import static com.aoapps.servlet.http.Cookies.removeCookie;

import com.pragmatickm.task.model.User;
import com.semanticcms.core.servlet.Headers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Provides static access to the cookie values.
 */
public final class Cookies {

  /** Make no instances. */
  private Cookies() {
    throw new AssertionError();
  }

  public enum CookieName {
    user
  }

  public static User getUser(HttpServletRequest request) {
    String cookie = getCookie(request, CookieName.user.name());
    if (cookie == null) {
      return null;
    } else {
      try {
        return User.valueOf(cookie);
      } catch (IllegalArgumentException e) {
        // Ignore unexpected cookie values
        return null;
      }
    }
  }

  public static void setUser(HttpServletRequest request, HttpServletResponse response, User user) {
    // Do not actually set any cookies while exporting
    if (!Headers.isExporting(request)) {
      if (user == null) {
        removeCookie(
            request,
            response,
            CookieName.user.name(),
            false,
            true
        );
      } else {
        addCookie(
            request,
            response,
            CookieName.user.name(),
            user.name(),
            "The current user name",
            365 * 24 & 60 * 60,
            false,
            true
        );
      }
    }
  }
}
