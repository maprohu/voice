%module libnl
%{
#include <net/if.h>
#include <errno.h>
#include <netlink/errno.h>
#include <netlink/netlink.h>
#include <netlink/genl/genl.h>
#include <netlink/genl/ctrl.h>
#include <linux/nl80211.h>
%}
%include "stdint.i"


unsigned int if_nametoindex(const char *ifname);
struct nl_sock* nl_socket_alloc	(void);
int genl_connect (struct nl_sock * sk);
int genl_ctrl_resolve(struct nl_sock *sk, const char *name);
struct nl_msg* nlmsg_alloc	(	void 		 );


enum nl_cb_kind {
   /** Default handlers (quiet) */
   NL_CB_DEFAULT,
   /** Verbose default handlers (error messages printed) */
   NL_CB_VERBOSE,
   /** Debug handlers for debugging */
   NL_CB_DEBUG,
   /** Customized handler specified by the user */
   NL_CB_CUSTOM,
   __NL_CB_KIND_MAX,
};
struct nl_cb* nl_cb_alloc	(	enum nl_cb_kind 	kind	)	;

enum {
 CTRL_CMD_UNSPEC,
 CTRL_CMD_NEWFAMILY,
 CTRL_CMD_DELFAMILY,
 CTRL_CMD_GETFAMILY,
 CTRL_CMD_NEWOPS,
 CTRL_CMD_DELOPS,
 CTRL_CMD_GETOPS,
 CTRL_CMD_NEWMCAST_GRP,
 CTRL_CMD_DELMCAST_GRP,
 CTRL_CMD_GETMCAST_GRP, /* unused */
 __CTRL_CMD_MAX,
};

void* genlmsg_put	(	struct nl_msg * 	msg,
uint32_t 	port,
uint32_t 	seq,
int 	family,
int 	hdrlen,
int 	flags,
uint8_t 	cmd,
uint8_t 	version
);

enum {
 CTRL_ATTR_UNSPEC,
 CTRL_ATTR_FAMILY_ID,
 CTRL_ATTR_FAMILY_NAME,
 CTRL_ATTR_VERSION,
 CTRL_ATTR_HDRSIZE,
 CTRL_ATTR_MAXATTR,
 CTRL_ATTR_OPS,
 CTRL_ATTR_MCAST_GROUPS,
 __CTRL_ATTR_MAX,
};

int nla_put_string	(	struct nl_msg * 	msg,
int 	attrtype,
const char * 	str
);
