/*
 Copyright 2013 FahnerIT

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package it.fahner.mywapi.http;

/**
 * Represents a numeric HTTP status code.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public enum HttpStatusCode {
	
	/* 1xx Informational status codes */
	Continue(100), SwitchingProtocols(101), Processing(102),
	
	/* 2xx Success status codes */
	OK(200), Created(201), Accepted(202), NonAuthoritativeInformation(203), NoContent(204),
	ResetContent(205), PartialContent(206), MultiStatus(207), AlreadyReported(208), IMUsed(226),
	
	/* 3xx Redirection status codes */
	MultipleChoices(300), MovedPermanently(301), Found(302), SeeOther(303), NotModified(304),
	UseProxy(305), SwitchProxy(306), TemporaryRedirect(307), PermanentRedirect(308),
	
	/* 4xx Client Error status codes */
	BadRequest(400), Unauthorized(401), PaymentRequired(402), Forbidden(403), NotFound(404), MethodNotAllowed(405),
	NotAcceptable(406), ProxyAuthenticationRequired(407), RequestTimeout(408), Conflict(409), Gone(410),
	LengthRequired(411), PreconditionFailed(412), RequestEntityTooLarge(413), RequestURITooLong(414),
	UnsupportedMediaType(415), RequestedRangeNotSatisfiable(416), ExpectationFailed(417), ImaTeapot(418),
	EnhanceYourCalm(420), UnprocessableEntity(422), Locked(423), FailedDependency(424), MethodFailed(424),
	UnorderedCollection(425), UpgradeRequired(426), PreconditionRequired(428), TooManyRequests(429),
	RequestHeaderFieldsTooLarge(431), NoResponse(444), RetryWith(449), BlockedByWindowsParentalControls(450),
	UnavailableForLegalReasons(451), FreedomRestricted(451),
	
	/* 5xx Server Error status codes */
	InternalServerError(500), NotImplemented(501), BadGateway(502), ServiceUnavailable(503), GatewayTimeout(504),
	HTTPVersionNotSupported(505), VariantAlsoNegotiates(506), InsufficientStorage(507), LoopDetected(508),
	BandwidthLimitExceeded(509), NotExtended(510), NetworkAuthenticationRequired(511), NetworkReadTimeoutError(598),
	NetworkConnectTimeoutError(599),
	
	/* Custom status code that is used when an unknown status code is received */
	MYWAPI_unknown(999);
	
	/** Stores the internal numeric HTTP status code. */
	private int code;
	
	/**
	 * Creates a new HttpStatus code.
	 * @param code The numeric HTTP status code
	 */
	private HttpStatusCode(int code) {
		this.code = code;
	}
	
	/**
	 * Returns the numeric status code of this HttpStatusCode.
	 * @since MyWebApi 1.0
	 * @return The numeric HTTP status code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Checks if this status code is always cacheable. This checks for the codes:
	 * 200, 203, 204, 205, 300, 301 and 410.
	 * @since MyWebApi 1.0
	 * @return TRUE if the status code allows for caching, FALSE if uncertain
	 */
	public boolean isAlwaysCacheable() {
		switch (code) {
		case 200: case 203: case 204: case 205: case 300: case 301: case 410: return true;
		default: return false;
		}
	}
	
	/**
	 * Returns the HTTP response class this HttpStatusCode belongs to.
	 * @since MyWebApi 1.0
	 * @return The HTTP response class this code belongs to
	 */
	public HttpResponseClass getResponseClass() {
		for (HttpResponseClass cls : HttpResponseClass.values()) {
			if (cls.isInThisClass(code)) { return cls; }
		}
		return HttpResponseClass.CLIENT_ERROR;
	}
	
	/**
	 * Returns an HttpStatusCode based on the status code given.
	 * @param code The code to return the HttpStatusCode constant for
	 * @return The HttpStatusCode that represents the code specified, returns code 999 if the
	 *  code specified is not known by this library
	 */
	public static HttpStatusCode fromCode(int code) {
		for (HttpStatusCode status : values()) {
			if (status.code == code) { return status; }
		}
		return MYWAPI_unknown;
	}
	
}
