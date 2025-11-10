import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const token = localStorage.getItem('jwtToken');

  if (!req.url.includes('/login') && token) {
    req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
  }

  let clonedRequest = req;
  if (token) {
    clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      }
    });
    console.log("🔐 AuthInterceptor added header:", clonedRequest.headers.get('Authorization'));
  }

  return next(clonedRequest).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 || error.status === 403) {
        localStorage.removeItem('jwtToken');
        router.navigate(['/dashboard']);
      }
      return throwError(() => error);
    })
  );
};
