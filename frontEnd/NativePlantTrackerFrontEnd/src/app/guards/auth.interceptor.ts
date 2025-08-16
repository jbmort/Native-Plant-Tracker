import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';




export const authInterceptor: HttpInterceptorFn = (req, next) => {
   const router = inject(Router);
  const authService = inject(AuthService);
  const authToken = localStorage.getItem('accessToken');

  if (authToken) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return next(authReq);
  }

  return next(req).pipe(
    catchError((error: any) => {
      if (error instanceof HttpErrorResponse) {
        if (error.status === 401) {
          console.error('Interceptor caught 401 error. Token invalid/expired. Redirecting to login.');
          
          authService.logout();

          router.navigate(['/login'], { queryParams: { reason: 'session_expired' } });
        };
         }
         return throwError(() => error);
        })
  );
};