declare module 'sockjs-client' {
  const SockJS: any;
  export default SockJS;
}

declare module '@stomp/stompjs' {
  export const Client: any;
  export type Message = any;
}
