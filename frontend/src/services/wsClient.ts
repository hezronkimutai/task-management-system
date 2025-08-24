import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import type { Message } from '@stomp/stompjs';

const WS_URL = (process.env.REACT_APP_API_BASE_URL || '') + '/ws';

class WSClient {
  client: InstanceType<typeof Client> | null = null;
  reconnectAttempts = 0;
  seenIds = new Set<number>();

  connect(onMessage: (msg: any) => void) {
    if (this.client && this.client.connected) return;

  const socket = new SockJS(WS_URL);
  // debug: log SockJS lifecycle events
  (socket as any).onopen = () => console.log('[ws] sockjs: open', WS_URL);
  (socket as any).onclose = () => console.log('[ws] sockjs: close');
  (socket as any).onmessage = (ev: any) => console.debug('[ws] sockjs: message', ev);
    const token = localStorage.getItem(process.env.REACT_APP_JWT_TOKEN_KEY || 'taskmanagement_token');

    this.client = new Client({
      webSocketFactory: () => socket as any,
      debug: () => {},
      reconnectDelay: 0, // we'll handle backoff manually
      onReconnect: () => {
        // noop: stompjs will call activate again
      }
    });

    this.client.onConnect = () => {
      this.reconnectAttempts = 0;
  console.log('[ws] stomp: connected');
  // subscribe to task events
  this.client?.subscribe('/topic/tasks', (message: Message) => {
        try {
          const body = JSON.parse(message.body);
          // dedupe events that we've already seen
          const id = body?.task?.id || body?.taskId;
          if (id && this.seenIds.has(Number(id))) return;
          if (id) this.seenIds.add(Number(id));
          console.log('[ws] stomp: message', body);
          onMessage(body);
        } catch (e) {}
      });
      // subscribe to notifications (global topic)
      this.client?.subscribe('/topic/notifications', (message: Message) => {
        try {
          const body = JSON.parse(message.body);
          console.log('[ws] notification', body);
          onMessage({ type: 'notification', payload: body });
        } catch (e) {}
      });
      // subscribe to per-user queue (messages sent via convertAndSendToUser)
      this.client?.subscribe('/user/queue/notifications', (message: Message) => {
        try {
          const body = JSON.parse(message.body);
          console.log('[ws] user notification', body);
          onMessage({ type: 'notification', payload: body });
        } catch (e) {}
      });
    };

    this.client.onStompError = (frame: any) => {
      // handle server error frames if needed
    };

    this.client.onWebSocketClose = () => {
  console.log('[ws] stomp: websocket closed, will attempt reconnect', this.reconnectAttempts + 1);
      // exponential backoff reconnect
      this.reconnectAttempts += 1;
      const delay = Math.min(30000, 1000 * Math.pow(2, Math.min(6, this.reconnectAttempts)));
      setTimeout(() => {
        try { this.client?.activate(); } catch (e) {}
      }, delay);
    };

    // attach Authorization header on connect
    if (token) {
      this.client.connectHeaders = { Authorization: `Bearer ${token}` } as any;
    }

  console.log('[ws] activating stomp client', { url: WS_URL, hasToken: !!token });
    this.client.activate();
  }

  disconnect() {
    try {
      this.client?.deactivate();
    } catch (e) {}
    this.client = null;
    this.seenIds.clear();
    this.reconnectAttempts = 0;
  }
}

export default new WSClient();
